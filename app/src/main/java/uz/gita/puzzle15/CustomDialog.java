package uz.gita.puzzle15;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import uz.gita.puzzle15.localdata.MySharedPref;

public class CustomDialog extends DialogFragment {

   public CustomDialog(){
        super(R.layout.dialog);
    }
    TextView winScore;
    private OnClickListener onClickListener;
    MySharedPref mySharedPref;

    @Override
    public int getTheme() {
        return R.style.DialogStile;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mySharedPref=MySharedPref.getInstance();
        winScore=view.findViewById(R.id.winScore);
        winScore.setText(String.valueOf(mySharedPref.getRecord1()));
        Button btnRefresh = view.findViewById(R.id.btnDialogRestart);
        setCancelable(false);
        btnRefresh.setOnClickListener(v -> {
            if (hasListener())
                onClickListener.onClick();
            dismiss();
        });

    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onResume() {
        super.onResume();

        WindowManager.LayoutParams params;
        params = Objects.requireNonNull(getDialog()).getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(params);
    }

    private boolean hasListener() {
        return onClickListener != null;
    }

    interface OnClickListener {
        void onClick();
    }
}
