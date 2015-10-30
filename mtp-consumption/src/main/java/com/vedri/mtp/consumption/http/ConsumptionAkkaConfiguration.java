package com.vedri.mtp.consumption.http;

import com.vedri.mtp.consumption.support.kafka.KafkaProducerActor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.BalancingPool;

import com.vedri.mtp.consumption.MtpConsumptionConstants;
import com.vedri.mtp.core.support.akka.ClusterAkkaConfiguration;
import com.vedri.mtp.core.support.akka.SpringExtension;

@Configuration
public class ConsumptionAkkaConfiguration extends ClusterAkkaConfiguration {

	public static final String CONSUMPTION_BALANCING_POOL_NAME = "consumptionBalancingPool";

	@Value(MtpConsumptionConstants.BALANCING_POOL_INSTANCES)
	private int balancingPoolInstances;

	@Bean
	public Props consumptionBalancingPoolProps(SpringExtension.SpringExt springExt) {
		return new BalancingPool(balancingPoolInstances).props(springExt.props(KafkaProducerActor.NAME));
	}

	@Override
	protected void doCreateClusteredRootActors(ActorSystem system, SpringExtension.SpringExt springExt) {
		super.doCreateClusteredRootActors(system, springExt);

		ActorRef clusterRootActor = system.actorOf(springExt.props(ConsumptionRootActor.NAME),
				ConsumptionRootActor.NAME);
	}

}
