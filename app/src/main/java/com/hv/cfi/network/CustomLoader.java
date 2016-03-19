package com.hv.cfi.network;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.hv.cfi.network.dto.Request;
import com.hv.cfi.network.dto.Response;


/**
 * Created by Himanshu Kumar singh
 */

public class CustomLoader extends AsyncTaskLoader<Response> {

    private Response mResponse;
    private Request mRequest;
    //    private DataObserver mObserver;
    private Utility utility;

    public CustomLoader(Context context, Request mRequest) {
        super(context);
        this.mRequest = mRequest;
        utility = new Utility(context);
    }


    @Override
    public Response loadInBackground() {
        Response response = null;
        if (mRequest.getRequestType().equalsIgnoreCase("get")) {
            response = utility.doGet(mRequest, mRequest.getRequestURL());
        } else if (mRequest.getRequestType().equalsIgnoreCase("post")) {
            if (mRequest.isfromJson())
                response = utility.doPost(mRequest, mRequest.getRequestURL(), mRequest.getJsonRequest());
//            else
//                response = utility.doPost(mRequest.getRequestID(),mRequest.getRequestURL(),mRequest.getStrRequest());
        }
        /*
        * Networking Fetching code will be here. All the downloading information should be here.
        *
        */
        return response;
    }


    @Override
    public void deliverResult(Response data) {
        if (isReset()) {
            // The Loader has been reset; ignore the result and invalidate the data.
            releaseResources(data);
            return;
        }

        // Hold a reference to the old data so it doesn't get garbage collected.
        // We must protect it until the new data has been delivered.
        Response oldResponse = mResponse;
        mResponse = data;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(data);
        }


        // At this point we can release the resources associated with
        // 'oldApps' if needed; now that the new result is delivered we
        // know that it is no longer in use.

        if (oldResponse != null && oldResponse != data) {
            releaseResources(oldResponse);
        }
    }


    @Override
    protected void onStartLoading() {


        if (mResponse != null) {
            // Deliver any previously loaded data immediately.
            deliverResult(mResponse);
        }

        // Begin monitoring the underlying data source.


//        if (mObserver == null) {
//            mObserver = new DataObserver(this);
//        }

//        if (takeContentChanged() || mResponse == null) {
        /*
         * This takeContentChanged() is called when the content of Oberver is changed.
         */

        if (mResponse == null) {
            // When the observer detects a change, it should call onContentChanged()
            // on the Loader, which will cause the next call to takeContentChanged()
            // to return true. If this is ever the case (or if the current data is
            // null), we force a new load.
            forceLoad();
        }
    }


    @Override
    protected void onStopLoading() {
//        dismissDialog();
//        super.onStopLoading();
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    public void onCanceled(Response data) {
        super.onCanceled(data);
        releaseResources(data);
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (mResponse != null) {
            releaseResources(mResponse);
        }

        // Stop monitoring for changes.
//        if (mObserver != null) {
//            getContext().unregisterReceiver(mObserver);
//            mObserver = null;
//        }

    }

    private void releaseResources(Response data) {
        // For a simple, there is nothing to do. For something like a Cursor, we
        // would close it in this method. All resources associated with the Loader
        // should be released here.
        data = null;
    }


    /**
     * For Observering the data change
     */
    /*public class DataObserver extends BroadcastReceiver {
        final CustomLoader mLoader;
        public DataObserver(CustomLoader loader) {
            mLoader = loader;
            IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
            filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
            filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
            filter.addDataScheme("package");
            mLoader.getContext().registerReceiver(this, filter);

            IntentFilter sdFilter = new IntentFilter();
            sdFilter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
            sdFilter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
            mLoader.getContext().registerReceiver(this, sdFilter);
        }

        @Override public void onReceive(Context context, Intent intent) {
            // Tell the loader about the change.
            mLoader.onContentChanged();
        }
    }
*/

}
