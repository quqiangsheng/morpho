/*package com.max256.morpho.common.test;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

public class VertxTest {
	
	
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx(new VertxOptions().setClustered(false));
		vertx.deployVerticle(MyVerticle.class.getName());
		EventBus bus=vertx.eventBus();
		MessageConsumer<String> consumer = bus.consumer("handle1");
		consumer.handler(message -> {
			System.out.println("I have received a message: " + message.body());
		});
		bus.publish("handle1", "Yay! Someone kicked a ball");
		
	}
	
}

*/