package obs;
import java.net.ConnectException;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonObject;

import io.obswebsocket.community.client.OBSRemoteController;
import io.obswebsocket.community.client.message.request.inputs.SetInputSettingsRequest;

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

	public OBSRemoteController getObsRemoteController() {
		return obsRemoteController;
	}

	// OBS Control Methods

	public void setSourceText(String sourceName, String text) {
		try {
			JsonObject settings = new JsonObject();
			settings.addProperty("text", text);

			SetInputSettingsRequest request = SetInputSettingsRequest.builder()
					.inputName(sourceName)
					.inputSettings(settings)
					.overlay(true)
					.build();

			obsRemoteController.sendRequest(request, response -> {
				System.out.println("Set text for source '" + sourceName + "' to: " + text);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
