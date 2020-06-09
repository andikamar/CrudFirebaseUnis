package org.dika.crudfirebaseunis.ui.share;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {

	private MutableLiveData<String> mText;

	public ShareViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue("BERBAGI ITU INDAH GAN");
	}

	public LiveData<String> getText() {
		return mText;
	}
}