package net.adamsmolnik.metrics.aws;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.util.EC2MetadataUtils;

/**
 * @author ASmolnik
 *
 */
public class AwsCustomMetrics implements AutoCloseable {

    private ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

    private final AmazonCloudWatch cw = new AmazonCloudWatchClient();

    public void launch() {
        String instanceId = EC2MetadataUtils.getInstanceId();
        Runtime rt = Runtime.getRuntime();

        ses.scheduleAtFixedRate(
                () -> {

                    try {
                        long memUsed = rt.totalMemory() - rt.freeMemory();
                        double memUsedPercent = (((double) memUsed) / rt.maxMemory()) * 100.0;
                        MetricDatum datum = new MetricDatum().withMetricName("JVMMemoryUtilization")
                                .withDimensions(new Dimension().withName("InstanceId").withValue(instanceId)).withTimestamp(new Date())
                                .withUnit(StandardUnit.Percent).withValue(memUsedPercent);

                        PutMetricDataRequest ec2Request = new PutMetricDataRequest().withNamespace("EC2/JVM").withMetricData(datum);
                        cw.putMetricData(ec2Request);
                        System.out.println("Metrics sent for ec2: " + ec2Request);

                        Optional<Tag> tagOptional = new AmazonEC2Client()
                                .describeInstances(new DescribeInstancesRequest().withInstanceIds(instanceId)).getReservations().stream().findFirst()
                                .get().getInstances().stream().findFirst().get().getTags().stream()
                                .filter(tag -> "aws:autoscaling:groupName".equals(tag.getKey())).findFirst();
                        tagOptional.ifPresent(t -> {
                            MetricDatum asgDatum = new MetricDatum().withMetricName("JVMMemoryUtilization")
                                    .withDimensions(new Dimension().withName("AutoScalingGroupName").withValue(t.getValue()))
                                    .withTimestamp(new Date()).withUnit(StandardUnit.Percent).withValue(memUsedPercent);

                            PutMetricDataRequest asgRequest = new PutMetricDataRequest().withNamespace("ASG/JVM").withMetricData(asgDatum);
                            cw.putMetricData(asgRequest);
                            System.out.println("Metrics sent for asg: " + asgRequest);
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }, 0, 15, TimeUnit.SECONDS);

    }

    public void close() {
        ses.shutdownNow();
    }

}
