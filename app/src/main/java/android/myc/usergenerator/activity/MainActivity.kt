package android.myc.usergenerator.activity

import android.content.Context
import android.myc.usergenerator.R
import android.myc.usergenerator.adapter.UserListAdapter
import android.myc.usergenerator.bean.UserBean
import android.myc.usergenerator.contract.MainContract
import android.myc.usergenerator.presenter.MainPresenter
import android.myc.usergenerator.view.RefreshRecyclerView
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), MainContract.View {
    var mPresenter: MainPresenter? = null
    private var mAdapter: UserListAdapter? = null
    private var usersList: RefreshRecyclerView? = null

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter()
        mPresenter!!.attachView(this)

        initViews()
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.getRandomUsers()

    }


    private fun initViews() {
        mAdapter = UserListAdapter(this)
        usersList = findViewById(R.id.rv_users)

        usersList?.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setOnBottomRefreshListener {
                mPresenter?.getRandomUsers()
            }
        }

        btRetry.setOnClickListener {
            mPresenter?.getRandomUsers()
        }


        spinnerGender.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if (position == 0) {
                    mPresenter?.getRandomUsers()
                }
                mPresenter?.getDataByGender(resources.getStringArray(R.array.gender_array)[position])
                rv_users.setOnBottomRefreshListener {
                    mPresenter?.getDataByGender(
                        resources.getStringArray(
                            R.array.gender_array
                        )[position]
                    )
                }
            }
        }

        spinnerNat.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if (position == 0) {
                    mPresenter?.getRandomUsers()
                }
                mPresenter?.getDataByNat(resources.getStringArray(R.array.nat_array)[position])
                rv_users.setOnBottomRefreshListener {
                    mPresenter?.getDataByNat(resources.getStringArray(R.array.nat_array)[position])
                }
            }

        }
    }

    @Suppress("DEPRECATION")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem: MenuItem = menu!!.findItem(R.id.action_search)

        val mSearchView = MenuItemCompat.getActionView(searchItem) as SearchView
        mSearchView.isIconified = true

        return super.onCreateOptionsMenu(menu)
    }

    override fun onError(throwable: Throwable?) {
        llRetry.visibility = View.VISIBLE

    }

    override fun onSuccess(list: List<UserBean>?) {
        llRetry.visibility = View.GONE
        mAdapter?.apply {
            mList.run {
                this?.addAll(this.size, list!!)
                this
            }
            notifyDataSetChanged()
        }
    }

    override fun clearData() {
        mAdapter?.mList?.clear()
    }

    override fun getContext(): Context {
        return this.applicationContext
    }


}
