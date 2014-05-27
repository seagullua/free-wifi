package app.freewifi.clases;


public class SelectedNet {
	private SelectedNet() {
	}

	private static SelectedNet instance;
	private WiFi _selected_wifi;
	
	public WiFi getSelectedItem(){
		return _selected_wifi;
	}
	public void setSelectedItem(WiFi new_selected_item){
		_selected_wifi = new_selected_item;
	}

	public static synchronized SelectedNet getInstance() {
		if (instance == null) {
			instance = new SelectedNet();
		}
		return instance;
	}

	
}