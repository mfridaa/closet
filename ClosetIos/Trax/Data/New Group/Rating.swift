//
//  Rating.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 15..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit
import CoreData

class Rating: NSManagedObject {
    class func numberOfToilets(InDatabase context : NSManagedObjectContext) throws -> Int{
        return try context.fetch(Rating.fetchRequest()).count
        
    }
    
    class func findOrCreateRating(matching rating: BasicRating, in context : NSManagedObjectContext,forToilet toilet : Toilet) throws -> Rating
    {
        let request: NSFetchRequest<Rating> = Rating.fetchRequest()
        // TODO: --add longitude to search
        
        request.predicate = NSPredicate(format: "id = %f", Int16(rating.id))
        do{
            let matches = try context.fetch(request)
            if matches.count > 0{
                //                assert(matches.count > 1, "Toilet.findOrCreateToilet -- inconsistencs")
                return matches[0]
            }
        } catch {
            print("error")
            throw error
        }
        
        let newRating = Rating(context: context)
        newRating.id = Int16(rating.id)
        newRating.comment = rating.description
        newRating.ratingNum = Int16(rating.ratingNum)
        newRating.rater = rating.rater
        if let toilet = try! Toilet.findToilet(withId: Int16(rating.id), in: context){
            newRating.toilet = toilet
        }
        
        //        newToilet.creator = toilet.creator
        return newRating
    }
    
    class func findRating(matchingClosetId id: String, in context : NSManagedObjectContext) throws -> [BasicRating]
    {
        let request: NSFetchRequest<Rating> = Rating.fetchRequest()
        request.predicate = NSPredicate(format: " = %@", id)
        do{
            var result = [BasicRating]()
            let matches = try context.fetch(request)
            for match in matches {
                let rt = BasicRating(id: Int(match.id), rater: match.rater!, ratingNum: Int(match.ratingNum), description: match.comment!)
                result.append(rt)
            }
            return result
            
        } catch {
            print("error")
            throw error
        }
    }
    
}
