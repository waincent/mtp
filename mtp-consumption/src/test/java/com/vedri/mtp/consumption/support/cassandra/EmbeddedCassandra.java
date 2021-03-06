package com.vedri.mtp.consumption.support.cassandra;

import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class EmbeddedCassandra {

	private String cassandraYamlFile = EmbeddedCassandraServerHelper.DEFAULT_CASSANDRA_YML_FILE;
	private String tmpDir = "build/embeddedCassandra";

    @PostConstruct
	public void start() throws Exception {
		EmbeddedCassandraServerHelper.startEmbeddedCassandra(cassandraYamlFile, tmpDir, 60000);
	}

    @PreDestroy
	public void stop() {
		EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
	}

	public void setCassandraYamlFile(String cassandraYamlFile) {
		this.cassandraYamlFile = cassandraYamlFile;
	}

	public void setTmpDir(String tmpDir) {
		this.tmpDir = tmpDir;
	}
}
