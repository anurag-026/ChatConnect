import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetchatappcompose.data.model.Category
import com.example.jetchatappcompose.data.model.Room

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatCard(item: Room, position: Int , onClick : () -> Unit) {

    Card(elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White , contentColor = Color.Black),
        onClick= onClick ,
        modifier = Modifier
            .background(
                Color.White,
                if (position % 2 == 0) RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp,
                    bottomStart = 24.dp,
                    bottomEnd = 0.dp
                ) else
                    RoundedCornerShape(
                        topStart = 24.dp,
                        topEnd = 24.dp,
                        bottomEnd = 24.dp,
                        bottomStart = 0.dp
                    )
            )
            .fillMaxHeight(.40F)
            .fillMaxWidth(.5F)


    ) {
        Column(
            modifier = Modifier
                .width(200.dp)
                .height(182.dp)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(alignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(1F)
                    .fillMaxHeight(.8F),
                painter = painterResource(id = Category.getCategoryImageByCategoryId(item.categoryId ?: 0)),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.padding(),
                textAlign = TextAlign.Justify,
                text = item.title ?: "",
                style = TextStyle( fontSize = 24.sp),
            )
        }
    }
}