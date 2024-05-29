resource "aws_lambda_function" "test_lambda" {
  # If the file is not in the current working directory you will need to include a
  # path.module in the filename.
  filename      = "${path.root}/../build/distributions/footballodds-0.0.1-SNAPSHOT.zip"
  function_name = "SpringLadbrokesOddsChecker_tf"
  role          = aws_iam_role.iam_for_lambda.arn
  handler       = "com.chrisp1985.footballodds.StreamLambdaHandler::handleRequest"

  runtime = "java21"
  timeout = 50
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
  policy_arn = "arn:aws:iam::aws:policy/CloudWatchLogsFullAccess"
}

resource "aws_iam_role_policy_attachment" "dynamo_fullaccess_policy_attachment" {
  role       = aws_iam_role.iam_for_lambda.id
  policy_arn = "arn:aws:iam::aws:policy/AmazonDynamoDBFullAccess"
}