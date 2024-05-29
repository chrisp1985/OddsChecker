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