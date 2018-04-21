//
//  File.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 19..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import Foundation

struct Day{
    static func getDayFor(day: DayOfWeek)->String{
        switch day {
        case .Monday:
            return "Monday"
        case .Tuesday:
            return "Tuesday"
        case .Wednesday:
            return "Wednesday"
        case .Thursday:
            return "Thursday"
        case .Friday:
            return "Friday"
        case .Saturday:
            return "Saturday"
        case .Sunday:
            return "Sunday"
        }
    }
    static func getDayFor(day: Int)->String{
        switch day {
        case 0:
            return "Monday"
        case 1:
            return "Tuesday"
        case 2:
            return "Wednesday"
        case 3:
            return "Thursday"
        case 4:
            return "Friday"
        case 5:
            return "Saturday"
        case 6:
            return "Sunday"
        default:
            return "no date"
        }
    }
}
enum DayOfWeek{
    case Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
}

