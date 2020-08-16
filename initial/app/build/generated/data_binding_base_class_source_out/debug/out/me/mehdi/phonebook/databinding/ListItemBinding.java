// Generated by view binder compiler. Do not edit!
package me.mehdi.phonebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import me.mehdi.phonebook.R;

public final class ListItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView fullName;

  @NonNull
  public final TextView phone;

  private ListItemBinding(@NonNull LinearLayout rootView, @NonNull TextView fullName,
      @NonNull TextView phone) {
    this.rootView = rootView;
    this.fullName = fullName;
    this.phone = phone;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ListItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.list_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ListItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.fullName;
      TextView fullName = rootView.findViewById(id);
      if (fullName == null) {
        break missingId;
      }

      id = R.id.phone;
      TextView phone = rootView.findViewById(id);
      if (phone == null) {
        break missingId;
      }

      return new ListItemBinding((LinearLayout) rootView, fullName, phone);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}