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