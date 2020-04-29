package com.example.vendorsapp.util.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class Utility {
    private Utility() {}

    public static void sendEmail(Context context, String subject, String message) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setDataAndType(Uri.parse("mailto:"), "text/plain");
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "a.ayushkumar1997@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);
        try {
            context.startActivity(Intent.createChooser(email, "Send mail..."));
            Log.i("Finished", "");
//            binding.helpEmailContent.setText("");
//            binding.helpEmailSubject.setText("");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
