package com.springbatch.bdpartitionerlocal.config;

import javax.sql.DataSource;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BDPartitionerConfig {
	@Bean
	public Partitioner pessoaPartitioner(
			@Qualifier("appDataSource") DataSource dataSource
			) {
		ColumnRangePartitioner partitioner = new ColumnRangePartitioner();
		partitioner.setTable("pessoa_origem");
		partitioner.setColumn("id");
		partitioner.setDataSource(dataSource);
		return partitioner;
		
	}
	
	@Bean
	public Partitioner dadosBancariosPartitioner(	
			@Qualifier("appDataSource") DataSource dataSource
			) {
		ColumnRangePartitioner partitioner = new ColumnRangePartitioner();
		partitioner.setTable("dados_bancarios_origem");
		partitioner.setColumn("id");
		partitioner.setDataSource(dataSource);
		return partitioner;
		
	}
}
