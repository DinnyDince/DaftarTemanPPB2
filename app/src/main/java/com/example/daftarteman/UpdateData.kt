package com.example.daftarteman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update_data.*

class UpdateData : AppCompatActivity() {
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekNama: String? = null
    private var cekAlamat: String? = null
    private var cekNoHP: String? = null
    private var cekJKel: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        supportActionBar!!.title = "UpdateData"

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        update.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                cekNama = new_nama.getText().toString()
                cekAlamat = new_alamat.getText().toString()
                cekNoHP = new_nohp.getText().toString()
                cekJKel = new_spinnerJK.selectedItem.toString()

                if (isEmpty(cekNama!!) || isEmpty(cekAlamat!!) || isEmpty(cekNoHP!!) || isEmpty(cekJKel!!)) {
                    Toast.makeText(this@UpdateData, "Data tidak boleh kosong",
                    Toast.LENGTH_SHORT).show()
                } else {
                    val setdata_teman = data_teman()
                    setdata_teman.nama = new_nama.text.toString()
                    setdata_teman.alamat = new_alamat.text.toString()
                    setdata_teman.no_hp = new_nohp.text.toString()
                    setdata_teman.jkel = new_spinnerJK.selectedItem.toString()
                    updateTeman(setdata_teman)
                }
            }
        })
    }

    private fun isEmpty(s: String): Boolean {
        return  TextUtils.isEmpty(s)

    }
    private val data: Unit
    private get() {
        val getNama = intent.extras!!.getString("dataNama")
        val getAlamat = intent.extras!!.getString("dataAlamat")
        val getNoHP = intent.extras!!.getString("dataNoHP")
        val getJKel = intent.extras!!.getString("dataJkel")

        new_nama!!.setText(getNama)
        new_alamat!!.setText(getAlamat)
        new_nohp!!.setText(getNoHP)
       // new_spinnerJK!!.setText(getJKel)
    }

    private fun updateTeman(teman: data_teman) {
        val userID = auth!!.uid
        val getKey = intent.extras!!.getString("getPrimaryKey")
        database!!.child("Admin")
            .child(userID!!)
            .child("DataTeman")
            .child(getKey!!)
            .setValue(teman)
            .addOnSuccessListener {
                new_nama!!.setText("")
                new_alamat!!.setText("")
                new_nohp!!.setText("")
              //  new_spinnerJK!!.setText("")
                Toast.makeText(this, "Data berhasil diubah",
                Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}