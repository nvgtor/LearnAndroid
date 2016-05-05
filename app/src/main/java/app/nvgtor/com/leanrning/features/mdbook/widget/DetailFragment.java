package app.nvgtor.com.leanrning.features.mdbook.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.nvgtor.com.leanrning.R;

/**
 * Created by nvgtor on 2016/5/4.
 */
public class DetailFragment extends Fragment {

    public static DetailFragment newInstance(String info){
        Bundle args = new Bundle();
        DetailFragment detailFragment = new DetailFragment();
        args.putString("info", info);
        detailFragment.setArguments(args);
        return detailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mbook_tab_fragment, null);
        TextView tvInfo = (TextView) view.findViewById(R.id.tvInfo);
        tvInfo.setText(getArguments().getString("info"));
        return view;
    }
}
