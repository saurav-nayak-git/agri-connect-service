output "primary_instance_public_ip" {
  description = "Public IP address of the primary instance"
  value       = oci_core_instance.primary_instance.public_ip
}

output "failover_instance_public_ip" {
  description = "Public IP address of the failover instance"
  value       = oci_core_instance.failover_instance.public_ip
}
