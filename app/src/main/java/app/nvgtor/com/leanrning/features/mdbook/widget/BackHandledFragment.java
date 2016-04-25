package app.nvgtor.com.leanrning.features.mdbook.widget;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by nvgtor on 2016/4/25.
 */
public abstract class BackHandledFragment extends Fragment{

    protected BackHandlerInterface backHandlerInterface;
    public abstract boolean onBackPressed();
    public abstract String getTagText();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof BackHandlerInterface)){
            throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
        } else {
            backHandlerInterface = (BackHandlerInterface) getActivity();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        backHandlerInterface.setSelectedFragment(this);
    }

    public interface BackHandlerInterface {
        public void setSelectedFragment(BackHandledFragment backHandledFragment);
    }
}
