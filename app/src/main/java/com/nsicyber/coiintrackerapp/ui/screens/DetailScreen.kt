package com.nsicyber.coiintrackerapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nsicyber.coiintrackerapp.ui.components.BaseView
import com.nsicyber.coiintrackerapp.ui.components.CustomAppBar
import com.nsicyber.coiintrackerapp.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(id: String?) {
    val viewModel = hiltViewModel<DetailViewModel>()
    var scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.getCoinDetail(id)
    }

    BaseView(

        useIsBusy = true,
        viewModel = viewModel,
        canGoBack = true,
        canScroll = true,
        isHorizontalPaddingEnabled = false,
        isVerticalPaddingEnabled = false
    ) { bottomSheetState ->
        Column(Modifier.fillMaxSize()) {
            Box {
                AsyncImage(
                    modifier = Modifier.height(300.dp),
                    model = viewModel.coinDetail?.image?.large,
                    contentDescription = "coin_image",
                    contentScale = ContentScale.Crop
                )
                CustomAppBar(
                    isTransparent = true,
                    isBackEnable = true,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(
                            Alignment.TopCenter
                        )
                )


            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(3.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = viewModel.coinDetail?.name ?: "",

                    // Body/Large/bold
                    style = TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 26.8.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF1B1725),
                    )
                )

                Text(
                    text = viewModel.coinDetail?.symbol ?: "",

                    // Body / Medium / medium
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 23.6.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF9D97AA),
                    )
                )

            }


            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {


                Text(
                    text = "Information",

                    // Body/Large/bold
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.8.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF1B1725),
                    )
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFF8FAFC), shape = RoundedCornerShape(size = 12.dp)
                        )
                        .padding(top = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {


                    viewModel.coinDetail?.description?.en?.let { value ->
                        if (value.isNotBlank()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Description",

                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 19.6.sp,
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF6E697A),
                                    )
                                )

                                Text(
                                    text = value,

                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 19.6.sp,
                                        fontWeight = FontWeight(700),
                                        color = Color(0xFF1B1725),
                                    )
                                )


                            }
                        }

                    }

                    viewModel.coinDetail?.hashing_algorithm?.let { value ->
                        if (value.isNotBlank()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = "Hashing Algorithm",

                                    // Body / Medium / medium
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 19.6.sp,
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF6E697A),
                                    )
                                )

                                Text(
                                    text = value,

                                    // Body / Medium / bold
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 19.6.sp,
                                        fontWeight = FontWeight(700),
                                        color = Color(0xFF1B1725),
                                        textAlign = TextAlign.Right,
                                    )
                                )


                            }
                        }
                    }

                    viewModel.coinDetail?.market_data?.current_price?.usd?.let { value ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Current Price (USD)",

                                // Body / Medium / medium
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    lineHeight = 19.6.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF6E697A),
                                )
                            )

                            Text(
                                text = "$ $value ",

                                // Body / Medium / bold
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    lineHeight = 19.6.sp,
                                    fontWeight = FontWeight(700),
                                    color = Color(0xFF1B1725),
                                    textAlign = TextAlign.Right,
                                )
                            )


                        }

                    }

                    viewModel.coinDetail?.market_data?.price_change_percentage_24h?.let { value ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Price Change Percentage (24h)",

                                // Body / Medium / medium
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    lineHeight = 19.6.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF6E697A),
                                )
                            )

                            Text(
                                text = "% $value ",

                                // Body / Medium / bold
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    lineHeight = 19.6.sp,
                                    fontWeight = FontWeight(700),
                                    color = Color(0xFF1B1725),
                                    textAlign = TextAlign.Right,
                                )
                            )


                        }
                    }


                }


            }


        }
    }
}