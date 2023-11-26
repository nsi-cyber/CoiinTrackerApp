package com.nsicyber.coiintrackerapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nsicyber.coiintrackerapp.R
import com.nsicyber.coiintrackerapp.model.CoinModel
import com.nsicyber.coiintrackerapp.ui.components.BaseView
import com.nsicyber.coiintrackerapp.ui.components.CustomAppBar
import com.nsicyber.coiintrackerapp.ui.components.CustomCard
import com.nsicyber.coiintrackerapp.ui.components.InputTextField
import com.nsicyber.coiintrackerapp.ui.components.navHostController
import com.nsicyber.coiintrackerapp.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    LaunchedEffect(Unit){
        viewModel.getCoinList()
        viewModel.searchTextField=""
    }


    BaseView(useIsBusy = true, viewModel = viewModel,
        isShowBottomBar = true,
        canGoBack = false,
        canScroll = false,
        isHorizontalPaddingEnabled = true,
        isVerticalPaddingEnabled = false
    ) {
        Column(Modifier.fillMaxSize()) {




            viewModel.filteredCoinList?.let {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)){
                    item{
                        CustomAppBar(isTransparent = false,
                            isBackEnable = false,
                            logo = {

                                Row() {
                                    Image(
                                        contentDescription = "logo",
                                        modifier = Modifier.size(24.dp),
                                        painter = painterResource(id = R.drawable.ic_logo)
                                    )
                                    Text(
                                        text = "CoiinTracker",

                                        // Body/Large/bold
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 20.8.sp,
                                            fontWeight = FontWeight(700),
                                            color = Color(0xFF1B1725),
                                        )
                                    )
                                }
                            },
                            suffixIcon = {
                                CustomCard(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(
                                            BorderStroke(0.5.dp, color = Color.Gray),
                                            RoundedCornerShape(8.dp)
                                        ),
                                    backgroundColor =  Color(0x8064748B)
                                ) {
                                    IconButton(onClick = { navHostController!!.popBackStack() }) {
                                        Icon(
                                            painterResource(id = R.drawable.ic_exit),
                                            "",
                                            tint = Color.Black,
                                            modifier = Modifier.padding(13.dp)
                                        )
                                    }
                                }
                            }
                        )
                    }
                    item {
                        InputTextField(
                            value = viewModel.searchTextField, onValueChange = {
                                viewModel.onSearchTextChanged(it)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = "Search for coins...",
                            backgroundColor = Color(0xFFF8FAFC),
                            unfocusedBorderColor = Color(0xFFE2E8F0),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_search),
                                    contentDescription = "search icon",
                                    tint = Color(0x8064748B)
                                )
                            }
                        )
                    }
                    items(viewModel.filteredCoinList?.size ?: 0) { index ->
                        CoinListItem(
                            model = viewModel.filteredCoinList?.get(index),
                            onClick = {
                                navHostController!!.navigate(
                                    "detail?id=${viewModel.filteredCoinList?.get(index)?.id}"
                                )
                            }
                        )

                    }
                }
            }



        }
    }
}


@Composable
fun CoinListItem(model: CoinModel?, onClick: (String?) -> Unit) {
    Row(
        modifier = Modifier
            .clickable {
                onClick(model?.id)
            }
            .border(
                width = 1.dp,
                color = Color(0xFFE2E8F0),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .fillMaxWidth()
            .background(color = Color(0xFFF8FAFC), shape = RoundedCornerShape(size = 8.dp))
            .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            text = "${model?.name} (${model?.symbol})",

            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 19.6.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF8E9BAE),
            )
        )


        Image(
            modifier = Modifier
                .padding(1.dp)
                .width(24.dp)
                .height(24.dp),
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "image description",
            contentScale = ContentScale.Fit
        )


    }

}