import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetchatappcompose.R

@Composable
fun ChatButton(buttonText: String, onButtonClick: () -> Unit) {
    Button(
        onClick = {
            onButtonClick()
        }, colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(
                id = R.color.blue

            ),
            contentColor = colorResource(id = R.color.white)
        ),
        shape = RoundedCornerShape(corner = CornerSize(6.dp)),
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = buttonText,
            style = TextStyle(color = colorResource(id = R.color.white), fontSize = 24.sp)
        )
        Spacer(modifier = Modifier.width(164.dp))
        Icon(
            modifier = Modifier
                .height(32.dp)
                .width(24.dp),
            painter = painterResource(id = R.drawable.ic_next),
            contentDescription = "icon arrow Right"
        )
    }
}