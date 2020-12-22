package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.User
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.databinding.ActivityProfileBinding
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.util.StringUtil
import kotlinx.io.IOException
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var viewModelFactory: ProfileViewModelFactory
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_TAKE_PHOTO = 1
    private val REQUEST_SELECT_IMAGE_IN_ALBUM = 0
    private var currentPhotoPath: String? = null
    private var currentUri: Uri? = null
    private var isEdit: Boolean = false

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val name = binding.nameEdit.text.toString()
        val email = binding.emailEdit.text.toString()
        outState.putBoolean("edit", isEdit)
        outState.putString("name", name)
        outState.putString("email", email)
        outState.putString("currentUri", currentUri?.toString())
        outState.putString("currentPhotoPath", currentPhotoPath)
    }

    private fun restoreEditState(name: String?, email: String?, uri: Uri?, photoPath: String?) {
        if(name != null && email != null) {
            show()
            binding.nameEdit.setText(name)
            binding.emailEdit.setText(email)
            currentUri = uri
            currentPhotoPath = photoPath
            if(uri != null) {
                setGalleryPic(uri)
            } else {
                binding.profPic.setImageResource(R.drawable.ic_person)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        viewModelFactory = ProfileViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)
        System.loadLibrary("native-string-util")

        setupToolbar()

        if(savedInstanceState != null) {
            val isEdit = savedInstanceState.getBoolean("edit")
            if(isEdit) {
                val name = savedInstanceState.getString("name")
                val email = savedInstanceState.getString("email")
                val uriStr = savedInstanceState.getString("currentUri")
                val uri = if(uriStr != null) {
                    Uri.parse(uriStr)
                } else {
                    null
                }
                val photoPath = savedInstanceState.getString("currentPhotoPath")
                restoreEditState(name, email, uri, photoPath)
            }
        }

        getData()
        binding.btnEdit.setOnClickListener { edit() }
        binding.btnSave.setOnClickListener { save() }
        binding.btnCancel.setOnClickListener { cancel() }
        binding.btnCamera.setOnClickListener { camera() }
        binding.btnGallery.setOnClickListener { gallery() }
        binding.btnRemovePhoto.setOnClickListener { remove() }
    }

    private fun getData() {
        viewModel.getData()
        viewModel.liveData.observe(this, Observer {
            updateView(it)
        })
    }

    private fun updateView(user: User) {
        binding.nameLabel.text = user.name
        binding.emailLabel.text = user.email
        if (user.profPic != "") {
            val uri = Uri.parse(user.profPic)
            binding.profPic.setImageURI(null)
            binding.profPic.setImageURI(uri)
        }
    }

    private fun show() {
        binding.nameLabel.visibility = View.GONE
        binding.emailLabel.visibility = View.GONE
        binding.btnEdit.visibility = View.GONE
        binding.nameField.visibility = View.VISIBLE
        binding.emailField.visibility = View.VISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.btnCancel.visibility = View.VISIBLE
        binding.btnCamera.visibility = View.VISIBLE
        binding.btnGallery.visibility = View.VISIBLE
        binding.btnRemovePhoto.visibility = View.VISIBLE
        isEdit = true
    }

    private fun hide() {
        binding.nameLabel.visibility = View.VISIBLE
        binding.emailLabel.visibility = View.VISIBLE
        binding.btnEdit.visibility = View.VISIBLE
        binding.nameField.visibility = View.GONE
        binding.emailField.visibility = View.GONE
        binding.btnSave.visibility = View.GONE
        binding.btnCancel.visibility = View.GONE
        binding.btnCamera.visibility = View.GONE
        binding.btnGallery.visibility = View.GONE
        binding.btnRemovePhoto.visibility = View.GONE
        isEdit = false
    }

    private fun edit() {
        val name = viewModel.liveData.value?.name
        val email = viewModel.liveData.value?.email
        show()
        binding.nameEdit.setText(name)
        binding.emailEdit.setText(email)
    }

    private fun save() {
        val name = StringUtil.capitalize(binding.nameEdit.text.toString())
        val email = binding.emailEdit.text.toString()
        val user = viewModel.liveData.value

        val response = viewModel.validateInput(name, email)
        if (response.isError) {
            val toast = Toast.makeText(this, response.message, Toast.LENGTH_LONG)
            toast.show()
        } else {
            if (user != null) {
                user.name = name
                user.email = email
                if (currentUri != null) {
                    user.profPic = currentUri.toString()
                } else {
                    user.profPic = ""
                }
                viewModel.updateData(user)
            }
            binding.nameLabel.text = name
            binding.emailLabel.text = email
            hide()
        }
    }

    private fun cancel() {
        hide()
        val user = viewModel.liveData.value
        if (user != null) {
            if (user.profPic != "") {
                val uri = Uri.parse(user.profPic)
                binding.profPic.setImageURI(null)
                binding.profPic.setImageURI(uri)
            } else {
                binding.profPic.setImageResource(R.drawable.ic_person)
            }
        }
    }

    private fun camera() {
        dispatchTakePictureIntent()
    }

    private fun gallery() {
        selectImageInAlbumIntent()
    }

    private fun remove() {
        currentPhotoPath = null
        currentUri = null
        binding.profPic.setImageResource(R.drawable.ic_person)
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.fileprovider",
                        it
                    )
                    currentUri = photoURI
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }

    private fun setPic() {
        // Get the dimensions of the View
        val targetW: Int = binding.profPic.width
        val targetH: Int = binding.profPic.height

        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true

            BitmapFactory.decodeFile(currentPhotoPath, this)

            val photoW: Int = outWidth
            val photoH: Int = outHeight

            // Determine how much to scale down the image
            val scaleFactor: Int =
                1.coerceAtLeast((photoW / targetW).coerceAtMost(photoH / targetH))

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inPurgeable = true
        }
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
            binding.profPic.setImageBitmap(bitmap)
        }
    }

    private fun selectImageInAlbumIntent() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }

    private fun setGalleryPic(uri: Uri) {
        binding.profPic.setImageURI(null)
        binding.profPic.setImageURI(uri)
        currentUri = uri
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            galleryAddPic()
            setPic()
        }
        if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM && resultCode == RESULT_OK) {
            if (data != null) {
                val uri = data.data!!
                setGalleryPic(uri)
            }
        }
    }


    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            android.R.id.home -> pressBack()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun pressBack(): Boolean {
        onBackPressed()
        return true
    }
}