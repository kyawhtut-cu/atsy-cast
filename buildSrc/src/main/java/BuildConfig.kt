import com.android.build.gradle.internal.dsl.BaseFlavor
import com.android.build.gradle.internal.dsl.BuildType

/*
* BuildType Ext
*/
fun BuildType.buildConfigInt(name: String, value: Int) = buildConfigField("int", name, "$value")

fun BuildType.buildConfigDouble(name: String, value: Double) =
    buildConfigField("double", name, "$value")

fun BuildType.buildConfigFloat(name: String, value: Float) =
    buildConfigField("float", name, "$value")

fun BuildType.buildConfigLong(name: String, value: Long) =
    buildConfigField("long", name, "$value")

fun BuildType.buildConfigString(name: String, value: String) =
    buildConfigField("String", name, "\"${value}\"")

fun BuildType.buildConfigBoolean(name: String, value: Boolean) =
    buildConfigField("boolean", name, "$value")

fun BuildType.buildConfigByteArray(name: String, value: String) =
    buildConfigField("Byte[]", name, value)

/*
* BaseFlavor Ext
*/

fun BaseFlavor.buildConfigInt(name: String, value: Int) = buildConfigField("int", name, "$value")

fun BaseFlavor.buildConfigDouble(name: String, value: Double) =
    buildConfigField("double", name, "$value")

fun BaseFlavor.buildConfigFloat(name: String, value: Float) =
    buildConfigField("float", name, "$value")

fun BaseFlavor.buildConfigLong(name: String, value: Long) =
    buildConfigField("long", name, "$value")

fun BaseFlavor.buildConfigString(name: String, value: String) =
    buildConfigField("String", name, "\"${value}\"")

fun BaseFlavor.buildConfigBoolean(name: String, value: Boolean) =
    buildConfigField("boolean", name, "$value")

fun BaseFlavor.buildConfigByteArray(name: String, value: String) =
    buildConfigField("Byte[]", name, value)
