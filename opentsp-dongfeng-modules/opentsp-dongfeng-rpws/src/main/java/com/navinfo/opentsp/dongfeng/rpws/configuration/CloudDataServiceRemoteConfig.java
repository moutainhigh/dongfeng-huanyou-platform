package com.navinfo.opentsp.dongfeng.rpws.configuration;

import com.navinfo.opentsp.dongfeng.common.rmi.BasicDataQueryWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.DataAnalysisWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.SynchronousTerminalDataWebService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.rmi.server.RMIClientSocketFactory;

//@Configuration
@Component
public class CloudDataServiceRemoteConfig {
	
	
	@Value("${rpws.client.rmi.port:1199}")
	private int rmiPort;

	@Resource(name="basicDataQueryWebServiceDao")
	private BasicDataQueryWebService basicDataQueryWebServiceDao;
	
	@Resource(name="dataAnalysisWebServiceDao")
	private DataAnalysisWebService dataAnalysisWebServiceDao;
	
	@Resource(name="synchronousTerminalDataWebServiceDao")
	private SynchronousTerminalDataWebService synchronousTerminalDataWebServiceDao;
	
	@Value("${rmi.client.timeout:5000}")
	private int clientTimeout;

//	@Value("${rmi.server.timeout:5000}")
//	private int serverTimeout;
	
	@Bean
	public RMIClientSocketFactory createRMIClientSocketFactory() {
		return new RMICustomClientSocketFactory(clientTimeout);
	}
	
//	@Bean 
//	public RMIServerSocketFactory createRMIServerSocketFactory() {
//		return new RMICustomServerSocketFactory(serverTimeout);
//	}
//	
	@Bean
	public RmiServiceExporter initBasicDataQueryWebServiceExport(RMIClientSocketFactory createRMIClientSocketFactory) {
		
		RmiServiceExporter exporter=new RmiServiceExporter();
		exporter.setServiceInterface(BasicDataQueryWebService.class);
		exporter.setServiceName("basicDataQueryWebService");
		exporter.setService(basicDataQueryWebServiceDao);
		exporter.setRegistryPort(rmiPort);
		
		exporter.setRegistryClientSocketFactory(createRMIClientSocketFactory);
//		exporter.setRegistryServerSocketFactory(createRMIServerSocketFactory);
		
		return exporter;
		
	}

	@Bean
	public RmiServiceExporter initDataAnalysisWebServiceExport(RMIClientSocketFactory createRMIClientSocketFactory) {
		
		RmiServiceExporter exporter=new RmiServiceExporter();
		exporter.setServiceInterface(DataAnalysisWebService.class);
		exporter.setServiceName("dataAnalysisWebService");
		exporter.setService(dataAnalysisWebServiceDao);
		exporter.setRegistryPort(rmiPort);
		exporter.setRegistryClientSocketFactory(createRMIClientSocketFactory);
//		exporter.setRegistryServerSocketFactory(createRMIServerSocketFactory);
		
		return exporter;
		
	}

	@Bean
	public RmiServiceExporter initSynchronousTerminalDataWebServiceExport(RMIClientSocketFactory createRMIClientSocketFactory) {
		
		RmiServiceExporter exporter=new RmiServiceExporter();
		exporter.setServiceInterface(SynchronousTerminalDataWebService.class);
		exporter.setServiceName("synchronousTerminalDataWebService");
		exporter.setService(synchronousTerminalDataWebServiceDao);
		exporter.setRegistryPort(rmiPort);
		
		exporter.setRegistryClientSocketFactory(createRMIClientSocketFactory);
//		exporter.setRegistryServerSocketFactory(createRMIServerSocketFactory);
		return exporter;
		
	}
}
