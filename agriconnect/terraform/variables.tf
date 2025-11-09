variable "tenancy_ocid" {}
variable "user_ocid" {}
variable "fingerprint" {}
variable "private_key_path" {}
variable "compartment_ocid" {}
variable "primary_region" {
  default = "us-phoenix-1"
}
variable "failover_region" {
  default = "us-ashburn-1"
}
variable "subnet_ocid" {}
variable "failover_subnet_ocid" {}
variable "image_ocid" {}
