import java.io.File

data class PersonNew(
        val name: String,
        val idNum: String,
        val province: String,
        val sex: String,
        val hotel: String,
        val startTime: Long,
        val endTime: Long
) {}

private val list = mutableListOf<PersonNew>()

fun getPersonNewRepository():List<PersonNew> {
    if(list.size != 0) return list

    File("repo.data").readLines().forEach {
        val split = it.split("\t")
        val name = split[0]
        val idNum = split[1]
        val province = split[2]
        val sex = split[3]
        val hotel = split[4]
        val startTime = split[5]
        val endTime = split[6]
        list.add(PersonNew(name, idNum, province, sex, hotel, startTime.toLong(), endTime.toLong()))
    }
    return list
}