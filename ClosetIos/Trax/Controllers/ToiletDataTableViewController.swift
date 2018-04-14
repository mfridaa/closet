//
//  ToiletDataTableViewController.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 13..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit
import CoreData
import MapKit

class ToiletDataTableViewController: ToiletTableViewViewController
{
    var container: NSPersistentContainer!
//    {
//        didSet{
//            print("asd")
//            countDataInDatabase()
//        }
//    }

    override func viewWillAppear(_ animated: Bool) {
        
        container?.performBackgroundTask{ contex in
            print( try! Toilet.allToilets(inDatabase: contex))
            do{
                let number = try Toilet.numberOfToilets(InDatabase: contex)
                DispatchQueue.main.async {
                    print(number)
                    self.numberOfDataInDatabase = number
                }
                
            }
            catch{
                DispatchQueue.main.async {
                    self.numberOfDataInDatabase = 0
                }
            }
        }
    }
    
    public var numberOfDataInDatabase:Int?
    
    
    private func countDataInDatabase() {
        container?.performBackgroundTask{ contex in
            do{
                let number = try Toilet.numberOfToilets(InDatabase: contex)
                DispatchQueue.main.async {
                    self.numberOfDataInDatabase = number
                }
                
            }
            catch{
                DispatchQueue.main.async {
                    self.numberOfDataInDatabase = 0
                }
            }
        }
    }
    
    override func addWayPoints(wayPoints: [BasicToilet]) {

        super.addWayPoints(wayPoints: wayPoints)
        updateDatabase(with: wayPoints)
    }
    
    private func updateDatabase(with toilets :[BasicToilet]){
        container?.performBackgroundTask{ contex in
            for toiletInfo in toilets{
                print("asd")
                _ = try? Toilet.findOrCreateToilet(matching: toiletInfo, in: contex)
            }
            try? contex.save()
        }
        printDatabaseStatistics()
    }
    
        override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
            if let navController = (segue.destination as? UINavigationController){
                let tableViewController = navController.viewControllers.compactMap({ $0 as? ToiletInformationsTableViewController})
                if let toiletInformationTableViewController = tableViewController.first,tableViewController.count == 1 {
                    if let annotation = (sender as? MKAnnotation),annotation.title != nil{
                        toiletInformationTableViewController.toiletName = annotation.title!
                        
    
                        if let name = annotation.title{
                            toiletInformationTableViewController.toiletName = name
                        }
                    }
    //                toiletInformationTableViewController.toiletName = "asd"
    
    
                }
            }
        }
    
    private func printDatabaseStatistics(){
        if let context = container?.viewContext{
//            let request: NSFetchRequest<Toilet> = Toilet.fetchRequest()
//            if let count = (try? context.fetch(Toilet.fetchRequest()))?.count {
//                print("\(count) toilets")
//            }
            if let count = try? context.count(for: Toilet.fetchRequest()) {
                print("\(count) toilets")
            }
        }
    }

}
