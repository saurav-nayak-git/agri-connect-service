terraform {
  required_providers {
    oci = {
      source  = "oracle/oci"
      version = "~> 4.60"
    }
  }
  required_version = ">= 1.0.0"
}

provider "oci" {
  tenancy_ocid     = var.tenancy_ocid
  user_ocid        = var.user_ocid
  fingerprint      = var.fingerprint
  private_key_path = var.private_key_path
  region           = var.primary_region
}

provider "oci" {
  alias            = "failover"
  tenancy_ocid     = var.tenancy_ocid
  user_ocid        = var.user_ocid
  fingerprint      = var.fingerprint
  private_key_path = var.private_key_path
  region           = var.failover_region
}
