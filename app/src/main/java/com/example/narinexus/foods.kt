package com.example.narinexus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FoodsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foods)

        val recycler = findViewById<RecyclerView>(R.id.recyclerFoods)
        recycler.layoutManager = GridLayoutManager(this, 2)
        recycler.adapter = FoodsAdapter(this, createFoods())

        findViewById<Button>(R.id.btnSearchMore).setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/search?q=healthy+food+recipes")
            )
            startActivity(intent)
        }
    }

    private fun createFoods(): List<FoodItem> {
        return listOf(
            FoodItem("f1", "Quinoa Salad", "Protein-rich & high fibre", R.drawable.quinoa_salad, "https://youtu.be/OQ9mU_zAtEs?si=79H6o9P3N7JR20hZ"),
            FoodItem("f2", "Mixed Berry Bowl", "Antioxidants for heart & skin", R.drawable.mixed_berry_bowl, "https://youtu.be/3-0vtEcjg08?si=zEWNR5qTewGabw-N"),
            FoodItem("f3", "Grilled Salmon", "Omega-3 rich for hormones", R.drawable.grilled_salmon, "https://youtu.be/j7BkpjD-HZ0?si=IQEOSwv9UrDqnoLR"),
            FoodItem("f4", "Avocado Toast", "Healthy fats & whole grain", R.drawable.avocado_toast, "https://youtu.be/AT5Rm60i81o?si=sGJYF1IEm57p2jTv"),
            FoodItem("f5", "Greek Yogurt Parfait", "Probiotics + protein", R.drawable.greek_yogurt_parfait, "https://youtu.be/6OOfOgKy7mY?si=kpVh-5jaDAIv3xoY"),
            FoodItem("f6", "Veggie Stir-Fry", "Vitamins from seasonal veggies", R.drawable.vegetable_stir_fry, "https://youtu.be/spDs_wzn8To?si=qZKOtmHeuQbn9nqI"),
            FoodItem("f7", "Lentil Soup", "Comforting protein & fibre", R.drawable.lentil_soup, "https://youtu.be/ZCVFUok4h6c?si=yWDHNt5EKS1SUcdd"),
            FoodItem("f8", "Oatmeal with Nuts", "Slow energy release", R.drawable.oatmeal_with_nuts, "https://youtu.be/3dyy1SyKwCE?si=CBmU4Dwg4pj4HrtF"),
            FoodItem("f9", "Chickpea Salad", "Plant protein & fibre", R.drawable.chickpea_salad, "https://youtu.be/PrJH8QFmtFE?si=u51NAbVZpo16CB_S"),
            FoodItem("f10", "Steamed Greens", "Iron & calcium packed", R.drawable.steamed_greens, "https://youtu.be/rJ3hU2xhYiA?si=r9f1mi6_W9yEJXx3"),
            FoodItem("f11", "Soba Noodle Bowl", "Light & satisfying", R.drawable.soba_noodle_bowl, "https://youtu.be/18ctNEFVMZw?si=7XX61-_TaSfnGSXT"),
            FoodItem("f12", "Grilled Chicken Salad", "Lean protein + greens", R.drawable.grilled_chicken_salad, "https://youtu.be/torITX5z3T0?si=eoFIo1sqYWh1I1_0"),
            FoodItem("f13", "Kale Smoothie", "Vitamin-rich green boost", R.drawable.kale_smoothie, "https://youtu.be/DUQaWmUz-7o?si=veOZwgEYvBasZk2A"),
            FoodItem("f14", "Brown Rice & Beans", "Sustained energy & fibre", R.drawable.brown_rice_and_beans, "https://youtu.be/7TwukNYFe10?si=LuhL8-WJQ2V-a4MP"),
            FoodItem("f15", "Roasted Sweet Potato", "Beta-carotene & potassium", R.drawable.roasted_sweet_potato, "https://youtu.be/Zjqh96BaNZo?si=uFbGkMAId_A7F-Db"),
            FoodItem("f16", "Tofu & Veggies", "Plant protein alternative", R.drawable.tofu_and_veggies, "https://youtu.be/RI5VE3IRjGc?si=tOmmnJfZdLKTxx8k"),
            FoodItem("f17", "Spinach Omelette", "Protein + leafy greens", R.drawable.spinach_omelette, "https://youtu.be/0KgwThTF93A?si=w6HsKbsKya_iZoFo"),
            FoodItem("f18", "Fruit & Nut Mix", "Quick nutrient snack", R.drawable.fruit_and_nut_mix, "https://youtu.be/6DK6p3vcRDA?si=pc7dp_Lv13kFNGc3"),
            FoodItem("f19", "Hummus & Veggies", "Protein & healthy fats", R.drawable.hummus_and_veggies, "https://youtu.be/EsZFJEjANiA?si=d_IUJ91y1wBrzBR8"),
            FoodItem("f20", "Mung Bean Khichdi", "Comforting & easy digestion", R.drawable.mung_bean_khichdi, "https://youtu.be/84Dxbk4n1ks?si=kLjf2692X6N_PUDP"),
            FoodItem("f21", "Sprouts Salad", "Vitamin C & protein", R.drawable.sprouts_salad, "https://youtu.be/gXTj3o0P0bE?si=CYgvP4APJ00FSg_c"),
            FoodItem("f22", "Baked Cod", "Lean sea protein", R.drawable.baked_cod, "https://youtu.be/9_7nU-MCF_8?si=lDv_V0JcXktX4692"),
            FoodItem("f23", "Paneer Tikka", "High protein & flavourful", R.drawable.paneer_tikka, "https://youtu.be/5wbMJFZfBBc?si=amGOX8g1EAbXSAUR"),
            FoodItem("f24", "Millet Upma", "Gluten-free grain option", R.drawable.millet_upma, "https://youtu.be/WURBzGR6BDA?si=7yw8NydN8Ef3ib25"),
            FoodItem("f25", "Sautéed Mushrooms", "Umami + vitamin D", R.drawable.sauteed_mushrooms, "https://youtu.be/Yu_mkN65E1I?si=Y6ZrO0Uhchj6aLY2"),
            FoodItem("f26", "Apple Cinnamon Oats", "Warm breakfast, filling", R.drawable.apple_cinnamon_oats, "https://youtu.be/y5IvPbsjhCw?si=IRiJOQeZGRFumbU6"),
            FoodItem("f27", "Beetroot Salad", "Detoxifying & iron-rich", R.drawable.beetroot_salad, "https://youtu.be/w9KldFo1o6M?si=HZfTAv6fMoF12wmr"),
            FoodItem("f28", "Cucumber Raita", "Cooling & probiotic", R.drawable.cucumber_raita, "https://youtu.be/SFgg6BfX97Q?si=BYuNv2b72f4tUD7D"),
            FoodItem("f29", "Black Bean Tacos", "Plant-protein tacos", R.drawable.black_bean_tacos, "https://youtu.be/JlrVEPbLc_A?feature=shared"),
            FoodItem("f30", "Misti Doi", "probiotic & gut-friendly", R.drawable.misti_doi, "https://youtu.be/z8a1wBs-a_w?si=mJNwrQCNqI8e1RjW")
        )
    }

}
