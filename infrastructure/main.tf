provider "aws" {
  region = "eu-west-2"
  access_key = var.access_key_value
  secret_key = var.secret_key_value
}

// EventBridge
resource "aws_iam_role_policy_attachment" "lambda_eventbridge_policy_attachment" {
  role       = aws_iam_role.eventbridge_role_tf.id
  policy_arn = "arn:aws:iam::aws:policy/AWSLambda_FullAccess"
}

resource "aws_iam_role_policy_attachment" "cloudwatch_eventbridge_policy_attachment" {
  role       = aws_iam_role.eventbridge_role_tf.id
  policy_arn = "arn:aws:iam::aws:policy/CloudWatchEventsFullAccess"
}

resource "aws_iam_role" "eventbridge_role_tf" {
  name               = "iam_for_eventbridge_tf"
  description = "EventBridge role to trigger Lambda (provisioned through Terraform).."
  assume_role_policy = jsonencode(
    {
      "Version": "2012-10-17",
      "Statement": [
        {
          "Effect": "Allow",
          "Principal": {
            "Service": "scheduler.amazonaws.com"
          },
          "Action": "sts:AssumeRole"
        }
      ]
    })
}

resource "aws_scheduler_schedule" "football_odds_checker_tf" {
  name       = "football_odds_checker_tf"
  description = "An eventbridge scheduler to trigger the football odds lambda (provisioned through Terraform)."
  group_name = "default"

  flexible_time_window {
    maximum_window_in_minutes = 30
    mode = "FLEXIBLE"
  }

  schedule_expression = "cron(0 */5 * * ? *)"

  target {
    arn      = aws_lambda_function.test_lambda.arn
    role_arn = aws_iam_role.eventbridge_role_tf.arn
  }
}

// Lambda

resource "aws_lambda_function" "test_lambda" {
  # If the file is not in the current working directory you will need to include a
  # path.module in the filename.
  filename      = "${path.module}/../build/distributions/footballodds-0.0.1-SNAPSHOT.zip"
  function_name = "SpringLadbrokesOddsChecker_tf"
  role          = aws_iam_role.iam_for_lambda.arn
  handler       = "com.chrisp1985.footballodds.StreamLambdaHandler::handleRequest"

  runtime = "java21"
  timeout = 45
  memory_size = 2056

  tags = {
    deployment = "terraform"
  }
}

resource "aws_iam_role" "iam_for_lambda" {
  name               = "iam_for_lambda"
  assume_role_policy = jsonencode(
    {
        "Version": "2012-10-17",
        "Statement": [{
            "Effect": "Allow",
            "Principal": {
                "Service": "lambda.amazonaws.com"
            },
            "Action": "sts:AssumeRole"
        }]
    })
}

resource "aws_cloudwatch_log_group" "lambda_log_group" {
  name              = "/aws/lambda/${aws_lambda_function.test_lambda.function_name}"
  lifecycle {
    prevent_destroy = false
  }
}

resource "aws_iam_role_policy_attachment" "lambda_fullaccess_policy_attachment" {
  role       = aws_iam_role.iam_for_lambda.id
  policy_arn = "arn:aws:iam::aws:policy/AWSLambda_FullAccess"
}

resource "aws_iam_role_policy_attachment" "cloudwatch_fullaccess_policy_attachment" {
  role       = aws_iam_role.iam_for_lambda.id
  policy_arn = "arn:aws:iam::aws:policy/CloudWatchEventsFullAccess"
}

resource "aws_iam_role_policy_attachment" "dynamo_fullaccess_policy_attachment" {
  role       = aws_iam_role.iam_for_lambda.id
  policy_arn = "arn:aws:iam::aws:policy/AmazonDynamoDBFullAccess"
}

//DynamoDB
resource "aws_dynamodb_table" "basic-dynamodb-table" {
  name           = "FootballResults_tf"
  billing_mode   = "PAY_PER_REQUEST"
  hash_key       = "event_id"

  attribute {
    name = "event_id"
    type = "S"
  }

  tags = {
    deployment = "terraform"
  }
}