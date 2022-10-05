package com.springbatch.bdpartitionerlocal.reader;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.database.support.SqlitePagingQueryProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.springbatch.bdpartitionerlocal.dominio.Pessoa;

@Configuration
public class BancoPessoaReaderConfig {
	@StepScope
	@Bean
	public JdbcPagingItemReader<Pessoa> pessoaReader(@Qualifier("appDataSource") DataSource dataSource,
			@Qualifier("queryProviderPessoa") PagingQueryProvider queryProviderPessoa) {
		return new JdbcPagingItemReaderBuilder<Pessoa>().name("pessoaReader").dataSource(dataSource)
				.queryProvider(queryProviderPessoa).pageSize(2000)
				.rowMapper(new BeanPropertyRowMapper<Pessoa>(Pessoa.class)).build();
	}

	@StepScope
	@Bean
	public SqlPagingQueryProviderFactoryBean queryProviderPessoa(
			@Value("#{stepExecutionContext['minValue']}") Long minValue,
			@Value("#{stepExecutionContext['maxValue']}") Long maxValue,
			@Qualifier("appDataSource") DataSource dataSource) {
		SqlPagingQueryProviderFactoryBean bean = new SqlPagingQueryProviderFactoryBean();
		bean.setSelectClause("*");
		bean.setFromClause("from pessoa_origem");
		bean.setWhereClause("where id >= " + minValue + " and " + " id <= " + maxValue);
		bean.setSortKey("id");
		bean.setDataSource(dataSource);
		return bean;
	}
}
