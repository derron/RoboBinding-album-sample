package org.robobinding.albumsample.activity;

import android.view.View;
import android.widget.TextView;

import org.robobinding.annotation.ViewBinding;
import org.robobinding.customviewbinding.CustomViewBinding;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@ViewBinding(simpleOneWayProperties = {"enabled"})
public class ViewBindingForView extends CustomViewBinding<View> {
}