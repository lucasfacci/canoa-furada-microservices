package com.api.fatura.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.api.fatura.beans.Fatura;
import com.api.fatura.daos.FaturaDAO;

@Service
public class FaturaConsumer {
	
	@Autowired
	FaturaDAO dao;

	@Value("${topic.name.consumer}")
	private String topicName;
	
	@KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
	public void consume(ConsumerRecord<String, String> payload) {
		String[] info = payload.value().split(";");
		Fatura fatura = new Fatura();
		fatura.setData(info[1]);
		fatura.setId_cabine(Integer.valueOf(info[0]));
		fatura.setTotal(Float.valueOf(info[2]));
		dao.save(fatura);
	}
}
