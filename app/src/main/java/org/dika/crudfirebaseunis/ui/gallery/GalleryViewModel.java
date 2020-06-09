package org.dika.crudfirebaseunis.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

	private MutableLiveData<String> mText;

	public GalleryViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue("GALERINYA GAN");
	}

	public LiveData<String> getText() {
		return mText;
	}
}