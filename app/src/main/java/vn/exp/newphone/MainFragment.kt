package vn.exp.newphone

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.ContextCompat
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.exp.newphone.databinding.FragmentMainBinding
import vn.exp.newphone.model.NumberPhone

class MainFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {


    private val PROJECTION: Array<out String> = arrayOf(
            /*
             * The detail data row ID. To make a ListView work,
             * this column is required.
             */
            ContactsContract.Data._ID,
            // The primary display name
            ContactsContract.Data.DISPLAY_NAME_PRIMARY,
            // The contact's _ID, to construct a content URI
//            ContactsContract.Data.CONTACT_ID,
            // The contact's LOOKUP_KEY, to construct a content URI
            ContactsContract.Data.LOOKUP_KEY

//            ContactsContract.Data.MIMETYPE,
//            ContactsContract.Data.DATA1
//            ContactsContract.Data.DATA2,
//            ContactsContract.Data.DATA3,
//            ContactsContract.Data.DATA4,
//            ContactsContract.Data.DATA5,
//            ContactsContract.Data.DATA6,
//            ContactsContract.Data.DATA7,
//            ContactsContract.Data.DATA8,
//            ContactsContract.Data.DATA9,
//            ContactsContract.Data.DATA10,
//            ContactsContract.Data.DATA11,
//            ContactsContract.Data.DATA12,
//            ContactsContract.Data.DATA13,
//            ContactsContract.Data.DATA14,
//            ContactsContract.Data.DATA15
    )

    private var mSearchString: String? = ""
    private val mSelectionArgs: Array<String> = arrayOf("")
    private val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 123

    /*
 * Constructs search criteria from the search string
 * and email MIME type
 */
    private val SELECTION: String =
            /*
             * Searches for an email address
             * that matches the search string
             */
            "${ContactsContract.CommonDataKinds.Email.ADDRESS} LIKE ? AND " +
                    /*
                     * Searches for a MIME type that matches
                     * the value of the constant
                     * Email.CONTENT_ITEM_TYPE. Note the
                     * single quotes surrounding Email.CONTENT_ITEM_TYPE.
                     */
                    "${ContactsContract.Data.MIMETYPE} = '${ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE}'"


    private lateinit var databinding: FragmentMainBinding
    private lateinit var eventTracks: ArrayList<NumberPhone>
    private lateinit var adapter: ListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissionRead()
    }

    private fun checkPermissionRead() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context!!,
                        Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,
                            Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity!!,
                        arrayOf(Manifest.permission.READ_CONTACTS),
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            loaderManager.initLoader(500, null, this)
            // Permission has already been granted
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    loaderManager.initLoader(500, null, this)
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

        // Add other 'when' lines to check for other
        // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventTracks = ArrayList()
        adapter = ListAdapter(eventTracks)
        databinding.recyclerEvents.layoutManager = LinearLayoutManager(context)
        databinding.recyclerEvents.adapter = adapter
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        mSelectionArgs[0] = "%$mSearchString%"
        // Starts the query
        return activity?.let {
            return CursorLoader(
                    it,
//                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                    ContactsContract.CommonDataKinds.SipAddress.,
//                    ContactsContract.Contacts.CONTENT_URI,
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            )
        } ?: throw IllegalStateException()
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        if (cursor!!.moveToFirst()) {
            do {
                val data = cursor.getString(cursor.getColumnIndex("display_name"))
                val data1 = cursor.getString(cursor.getColumnIndex("_id"))
                val track = NumberPhone(data1, data)
                eventTracks.add(track)
                // do what ever you want here
            } while (cursor.moveToNext())
        }
        cursor.close()
        adapter.notifyDataSetChanged()
        stopLoader(500)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }

    fun stopLoader(id: Int) {
        loaderManager.destroyLoader(id)
    }
}