package com.myapp.doctorapp.interfaces;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public interface OnFragmentButtonClickListener {

    void onButtonClicked(int id, Fragment fragment, Bundle bundle);
}
