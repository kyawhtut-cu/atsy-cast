import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.api.LibraryVariant
import com.android.build.api.dsl.VariantDimension

/*
* buildConfigField
*/
fun ApplicationDefaultConfig.buildConfigBoolean(name: String, value: Boolean) =
    buildConfigField("boolean", name, "$value")

fun ApplicationBuildType.buildConfigBoolean(name: String, value: Boolean) =
    buildConfigField("boolean", name, "$value")

fun ApplicationVariant.buildConfigBoolean(name: String, value: Boolean) =
    buildConfigField("boolean", name, "$value")

fun ApplicationDefaultConfig.buildConfigByteArray(name: String, value: String) =
    buildConfigField("Byte[]", name, value)

fun ApplicationBuildType.buildConfigByteArray(name: String, value: String) =
    buildConfigField("Byte[]", name, value)

fun ApplicationVariant.buildConfigByteArray(name: String, value: String) =
    buildConfigField("Byte[]", name, value)

fun ApplicationDefaultConfig.buildConfigDouble(name: String, value: Double) =
    buildConfigField("double", name, "$value")

fun ApplicationBuildType.buildConfigDouble(name: String, value: Double) =
    buildConfigField("double", name, "$value")

fun ApplicationVariant.buildConfigDouble(name: String, value: Double) =
    buildConfigField("double", name, "$value")

fun ApplicationDefaultConfig.buildConfigFloat(name: String, value: Float) =
    buildConfigField("float", name, "$value")

fun ApplicationBuildType.buildConfigFloat(name: String, value: Float) =
    buildConfigField("float", name, "$value")

fun ApplicationVariant.buildConfigFloat(name: String, value: Float) =
    buildConfigField("float", name, "$value")

fun ApplicationDefaultConfig.buildConfigInt(name: String, value: Int) =
    buildConfigField("int", name, "$value")

fun ApplicationBuildType.buildConfigInt(name: String, value: Int) =
    buildConfigField("int", name, "$value")

fun VariantDimension.buildConfigInt(name: String, value: Int) =
    buildConfigField("int", name, "$value")

fun ApplicationVariant.buildConfigInt(name: String, value: Int) =
    buildConfigField("int", name, "$value")

fun ApplicationDefaultConfig.buildConfigLong(name: String, value: Long) =
    buildConfigField("long", name, "$value")

fun ApplicationBuildType.buildConfigLong(name: String, value: Long) =
    buildConfigField("long", name, "$value")

fun ApplicationVariant.buildConfigLong(name: String, value: Long) =
    buildConfigField("long", name, "$value")

fun ApplicationDefaultConfig.buildConfigString(name: String, value: String) =
    buildConfigField("String", name, "\"${value}\"")

fun ApplicationBuildType.buildConfigString(name: String, value: String) =
    buildConfigField("String", name, "\"${value}\"")

fun ApplicationVariant.buildConfigString(name: String, value: String) =
    buildConfigField("String", name, "\"${value}\"")

fun LibraryVariant.buildConfigString(name: String, value: String) =
    buildConfigField("String", name, "\"${value}\"")

fun VariantDimension.buildConfigString(name: String, value: String) =
    buildConfigField("String", name, "\"${value}\"")

fun ApplicationVariant.buildConfigStrings(vararg fields: Pair<String, String>) {
    fields.forEach { (key, value) ->
        buildConfigField("String", key, "\"${value}\"")
    }
}

/*
* resValue
*/
fun ApplicationDefaultConfig.resValueBoolean(name: String, value: Boolean) =
    resValue("bool", name, "$value")

fun ApplicationBuildType.resValueBoolean(name: String, value: Boolean) =
    resValue("bool", name, "$value")

fun ApplicationVariant.resValueBoolean(name: String, value: Boolean) =
    resValue("bool", name, "$value")

fun ApplicationDefaultConfig.resValueInt(name: String, value: Int) =
    resValue("integer", name, "$value")

fun ApplicationBuildType.resValueInt(name: String, value: Int) = resValue("integer", name, "$value")

fun ApplicationVariant.resValueInt(name: String, value: Int) = resValue("integer", name, "$value")

fun ApplicationDefaultConfig.resValueString(name: String, value: String) =
    resValue("string", name, value)

fun ApplicationBuildType.resValueString(name: String, value: String) =
    resValue("string", name, value)

fun ApplicationVariant.resValueString(name: String, value: String) =
    resValue("string", name, value)
