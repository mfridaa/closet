//
//  NameAndLatitudeAndLongitudeAndOpeningHours.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 22..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

struct NameAndLatitudeAndLongitudeAndOpeningHours :Decodable,Encodable{
    let name:String
    let latitude:Float
    let longitude:Float
    let openingHours:[OpeningHour]
}
