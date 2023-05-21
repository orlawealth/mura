package com.example.ola.models

import org.json.JSONObject

class Course(
    val buildingName: String,
    val room: String,
    val lecturer: String,
    private val phoneNumber: String,
    val courseName: String,
    val description: String,
    val day: String,
    val time: String
) {

    constructor(map: JSONObject) : this(
        buildingName = map["buildingName"] as String,
        room = map["room"] as String,
        lecturer = map["lecturer"] as String,
        phoneNumber = map["phoneNumber"] as String,
        courseName = map["courseName"] as String,
        description = map["description"] as String,
        day = map["day"] as String,
        time = map["time"] as String
    )

}
