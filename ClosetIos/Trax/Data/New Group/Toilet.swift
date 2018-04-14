//
//  Toilet.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 13..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit
import CoreData

class Toilet: NSManagedObject
{
    class func findOrCreateToilet(matching toilet: BasicToilet, in context : NSManagedObjectContext) throws -> Toilet
    {
        let request: NSFetchRequest<Toilet> = Toilet.fetchRequest()
        // TODO: --add longitude to search
        
        request.predicate = NSPredicate(format: "latitude = %@", toilet.location.latitude)
        do{
            let matches = try context.fetch(request)
            if matches.count > 0{
                assert(matches.count > 1, "Toilet.findOrCreateToilet -- inconsistencs")
                return matches[0]
            }
        } catch {
            throw error
        }
       
        let newToilet = Toilet(context: context)
        newToilet.latitude = toilet.location.latitude
        newToilet.longitude = toilet.location.longitude
        newToilet.name = toilet.name
//        newToilet.creator = toilet.creator
        newToilet.status = toilet.status
        return newToilet
    }
}
