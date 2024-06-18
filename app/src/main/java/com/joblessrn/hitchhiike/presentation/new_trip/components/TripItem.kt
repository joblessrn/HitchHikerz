package com.joblessrn.hitchhiike.presentation.new_trip.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.joblessrn.hitchhiike.R

@Composable
fun TripItem(){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize().padding(5.dp)) {
            val glStart = createGuidelineFromStart(0.05f)
            val glTop = createGuidelineFromTop(0.05f)
            val glEnd = createGuidelineFromEnd(0.05f)
            val glBot = createGuidelineFromBottom(0.05f)
            val (cityFrom,cityTo,iconTo, price, iconPassengers,
                numPassengers,timeDeparture,timeArrive,driverInfo,carType) = createRefs()

            Text(text = "14:00",
                modifier = Modifier.constrainAs(timeDeparture){
                    start.linkTo(glStart)
                    top.linkTo(glTop)
                },
                fontSize = 15.sp)
            Text(text = "Казань",
                modifier = Modifier.constrainAs(cityFrom){
                    start.linkTo(timeDeparture.end,margin = 8.dp)
                    top.linkTo(glTop)
                },
                fontSize = 20.sp)

            Icon(
                modifier = Modifier.constrainAs(iconTo){
                    top.linkTo(cityFrom.bottom, margin = 8.dp)
                    start.linkTo(glStart)
                },
                painter = painterResource(id = R.drawable.icon_arrow_down_24),
                contentDescription = "null"
            )

            Text(text = "17:00",
                modifier = Modifier.constrainAs(timeArrive){
                    start.linkTo(glStart)
                    top.linkTo(iconTo.bottom, margin = 8.dp)
                },
                fontSize = 15.sp)
            Text(text = "Альмет",
                modifier = Modifier.constrainAs(cityTo){
                    start.linkTo(timeArrive.end,margin = 8.dp)
                    top.linkTo(iconTo.bottom, margin = 8.dp)
                },
                fontSize = 20.sp)

            Text(text = "500 P",
                modifier = Modifier.constrainAs(price){
                    end.linkTo(glEnd)
                    top.linkTo(glTop)
                },
                fontSize = 30.sp)

            Icon(//painter = painterResource(id = R.drawable.icon_seat_24),
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "",
                modifier = Modifier.constrainAs(iconPassengers){
                    end.linkTo(numPassengers.start, margin = 8.dp)
                    top.linkTo(cityTo.top)
                })

            Text(text = "3/4",
                modifier = Modifier.constrainAs(numPassengers){
                    end.linkTo(glEnd)
                    top.linkTo(cityTo.top)
                },
                fontSize = 20.sp)

            Row(modifier = Modifier
                .width(130.dp)
                .height(70.dp)
                .constrainAs(driverInfo) {
                    start.linkTo(glStart)
                    bottom.linkTo(glBot)
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                Image(
                    painter = painterResource(id = R.drawable.avatar_image),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(4.dp, 4.dp)
                        .clip(CircleShape)
                        .size(60.dp),
                    //colorFilter = ColorFilter.tint(colorResource(id = R.color.white)),
                    contentScale = ContentScale.Crop,
                )
                Column(modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(text = "Рома",
                        modifier = Modifier
                    )
                    Text(text = "рейтинг")
                }
            }
            Icon(modifier = Modifier
                .size(50.dp)
                .constrainAs(carType){
                    end.linkTo(glEnd)
                    bottom.linkTo(glBot)
                },
                painter = painterResource(id = R.drawable.icon_sedan_40),
                contentDescription = "type of vehicle")

        }
    }
}

@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun Prev(){
    TripItem()
}