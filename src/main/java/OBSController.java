import java.net.ConnectException;

import io.obswebsocket.community.client.OBSRemoteController;

public class OBSController {
	private OBSRemoteController obsRemoteController;
	String host;
	int port;
	String password;

	public OBSController(String host, int port, String password) {
		this.host = host;
		this.port = port;
		this.password = password;
		this.createOBSRemoteController();

		this.connect();
	}

	private void createOBSRemoteController() {
		try {
			this.obsRemoteController = OBSRemoteController.builder()
					.autoConnect(false)
					.host(host)
					.port(port)
					.password(password)
					.lifecycle()
					.onReady(this::onReady)
					.and()
					.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		obsRemoteController.connect();
	}

	private void onReady() {
		System.out.println("OBS Remote Controller is ready and connected.");
	}


}
