data "oci_identity_availability_domains" "primary_ad" {
  compartment_id = var.compartment_ocid
  region         = var.primary_region
}

resource "oci_core_instance" "primary_instance" {
  availability_domain = data.oci_identity_availability_domains.primary_ad.availability_domains[0].name
  compartment_id      = var.compartment_ocid
  shape               = "VM.Standard2.1"

  create_vnic_details {
    subnet_id        = var.subnet_ocid
    assign_public_ip = true
  }

  source_details {
    source_type = "image"
    image_id    = var.image_ocid
  }

  metadata = {
    ssh_authorized_keys = file("~/.ssh/id_rsa.pub")
  }

  display_name = "agriconnect-primary-instance"
  region       = var.primary_region
}

data "oci_identity_availability_domains" "failover_ad" {
  compartment_id = var.compartment_ocid
  region         = var.failover_region
}

resource "oci_core_instance" "failover_instance" {
  provider            = oci.failover
  availability_domain = data.oci_identity_availability_domains.failover_ad.availability_domains[0].name
  compartment_id      = var.compartment_ocid
  shape               = "VM.Standard2.1"

  create_vnic_details {
    subnet_id        = var.failover_subnet_ocid
    assign_public_ip = true
  }

  source_details {
    source_type = "image"
    image_id    = var.image_ocid
  }

  metadata = {
    ssh_authorized_keys = file("~/.ssh/id_rsa.pub")
  }

  display_name = "agriconnect-failover-instance"
  region       = var.failover_region
}
