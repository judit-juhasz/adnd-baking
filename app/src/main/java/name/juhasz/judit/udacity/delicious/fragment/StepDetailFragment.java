package name.juhasz.judit.udacity.delicious.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import name.juhasz.judit.udacity.delicious.R;
import name.juhasz.judit.udacity.delicious.model.Step;

public class StepDetailFragment extends Fragment {

    public static final String STEP_DATA = "STEP_DATA";

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View rootView =
                inflater.inflate(R.layout.fragment_step_detail, container, false);

        final Bundle arguments = getArguments();
        final Step step = (Step) arguments.getParcelable(STEP_DATA);

        final TextView description = (TextView) rootView.findViewById(R.id.tv_step_description);
        description.setText(step.getDescription());

        return rootView;
    }
}