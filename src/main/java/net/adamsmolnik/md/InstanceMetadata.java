package net.adamsmolnik.md;

import java.util.Optional;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.util.EC2MetadataUtils;

/**
 * @author ASmolnik
 *
 */
public class InstanceMetadata {

	public String fetch() {
		StringBuilder sb = new StringBuilder();
		try {
			String instanceId = EC2MetadataUtils.getInstanceId();
			sb.append("ec2 instance id: ");
			sb.append(instanceId);
			sb.append(", availability zone: ");
			sb.append(EC2MetadataUtils.getAvailabilityZone());

			EC2MetadataUtils.getNetworkInterfaces().stream().map(ni -> {
				return ", network interface: " + ni.getPublicIPv4s() + ", " + ni.getPublicHostname() + ", " + ni.getSubnetIPv4CidrBlock();
			}).forEach(sb::append);

			Optional<Tag> tagOptional = new AmazonEC2Client().describeInstances(new DescribeInstancesRequest().withInstanceIds(instanceId))
					.getReservations().stream().findFirst().get().getInstances().stream().findFirst().get().getTags().stream()
					.filter(tag -> "aws:autoscaling:groupName".equals(tag.getKey())).findFirst();
			tagOptional.ifPresent(t -> sb.append(", ").append(t.getKey()).append("=").append(t.getValue()));
		} catch (Exception e) {
			sb.append("Exception occured: " + e.getLocalizedMessage());
		}
		return sb.length() > 0 ? sb.toString() : "No metadata available";
	}

}
