import androidx.compose.material3.lightColorScheme
import com.example.plantmate.ui.theme.BackgroundLight
import com.example.plantmate.ui.theme.OnBackgroundLight
import com.example.plantmate.ui.theme.OnPrimaryLight
import com.example.plantmate.ui.theme.OnSecondaryLight
import com.example.plantmate.ui.theme.OnSurfaceLight
import com.example.plantmate.ui.theme.OnSurfaceVariantLight
import com.example.plantmate.ui.theme.OnTertiaryLight
import com.example.plantmate.ui.theme.PrimaryLight
import com.example.plantmate.ui.theme.SecondaryLight
import com.example.plantmate.ui.theme.SurfaceLight
import com.example.plantmate.ui.theme.SurfaceVariantLight
import com.example.plantmate.ui.theme.TertiaryLight

val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,

    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,

    tertiary = TertiaryLight,
    onTertiary = OnTertiaryLight,

    background = BackgroundLight,
    onBackground = OnBackgroundLight,

    surface = SurfaceLight,
    onSurface = OnSurfaceLight,

    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight
)
