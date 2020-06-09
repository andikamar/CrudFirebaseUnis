package org.dika.crudfirebaseunis.ui.tools;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ToolsViewModel extends ViewModel {

	private MutableLiveData<String> mText;

	public ToolsViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue("TOOLS NYA BOS Q");
	}

	public LiveData<String> getText() {
		return mText;
	}
}