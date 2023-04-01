package com.shi.shigo.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.billingclient.api.*
import com.shi.shigo.adapter.GetMoneyAdapter
import com.shi.shigo.databinding.FragmentBasketBinding
import com.shi.shigo.model.GetMoneyRow

class BasketFragment : Fragment() {
    private lateinit var binding: FragmentBasketBinding
    private lateinit var adapter: GetMoneyAdapter
    private var products: MutableList<GetMoneyRow> = mutableListOf()
    private var productsSku: MutableList<SkuDetails> = mutableListOf()
    private var productsSkuSorted: MutableList<SkuDetails> = mutableListOf()
    private lateinit var billingClient: BillingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        products.add(GetMoneyRow("50 Shi", "10 TRY"))
        products.add(GetMoneyRow("100 Shi", "20 TRY"))
        products.add(GetMoneyRow("200 Shi", "40 TRY"))
        products.add(GetMoneyRow("500 Shi", "100 TRY"))
        products.add(GetMoneyRow("1000 Shi", "200 TRY"))

        val purchasesUpdatedListener =
            PurchasesUpdatedListener { billingResult, purchases ->

            }

        billingClient = BillingClient.newBuilder(requireContext())
            .enablePendingPurchases()
            .setListener(purchasesUpdatedListener)
            .build()

        connectToGooglePlayBilling()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBasketBinding.inflate(layoutInflater, container,false)

        initAdapter()

        return binding.root
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvMoney.layoutManager = layoutManager
        adapter = GetMoneyAdapter((requireContext()), products,this)
        binding.rvMoney.adapter = adapter
    }

    private fun connectToGooglePlayBilling() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode ==  BillingClient.BillingResponseCode.OK) {
                    getProductDetails()
                }
            }
            override fun onBillingServiceDisconnected() {
                connectToGooglePlayBilling()
            }
        })
    }

    private fun getProductDetails(){
        val productIds: ArrayList<String> = ArrayList()
        productIds.add("shi_50")
        productIds.add("shi_100")
        productIds.add("shi_200")
        productIds.add("shi_500")
        productIds.add("shi_1000")

        val getProductDetailsQuery = SkuDetailsParams
            .newBuilder()
            .setSkusList(productIds)
            .setType(BillingClient.ProductType.INAPP)
            .build()

        billingClient.querySkuDetailsAsync(
            getProductDetailsQuery
        ) { billingResult, list ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && !list.isNullOrEmpty()) {
                try {
                    list.forEach {
                        productsSku.add(it)
                        //products.add(GetMoneyRow(100, it.price))
                    }

                } catch (e: Exception) {

                }
                productsSkuSorted = productsSku.sortedBy { it.description.toInt() } as MutableList<SkuDetails>
            }
        }
    }

    fun launchBilling(index: Int) {
        billingClient.launchBillingFlow(
            requireActivity(),
            BillingFlowParams.newBuilder().setSkuDetails(productsSkuSorted[index]).build()
        )
    }

}