//
//  NavigationTabBarViewController.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 12..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit
import MapKit
import CoreData

class NavigationTabBarViewController: UITabBarController {

    var container:NSPersistentContainer? = (UIApplication.shared.delegate as! AppDelegate).persistentContainer
    
    override func viewWillAppear(_ animated: Bool) {
        updateContexOfControllers()
    }
    
    private var databaseChanged:((_:NavigationTabBarViewController)->Void) = { controller in
        controller.updateContexOfControllers()
    }
    
    private func updateContexOfControllers(){
        if let controllers = viewControllers{
            for controller in controllers{
                if let toiletController = ( controller as? ToiletDataTableViewController )  {
                    refreshToilets(force: false)
                    toiletController.container = container
                }
            }
        }
    }
    
    override func tabBar(_ tabBar: UITabBar, didSelect item: UITabBarItem){
        //TODO: --set the views data from database
        
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let navigationController = segue.destination as? UINavigationController{
            if let annotation = ( sender as? ToiletMKPointAnnotation ){
                if let toiletInformationsViewController = navigationController.childViewControllers[0] as? ToiletInformationsTableViewController {
                    toiletInformationsViewController.toilet = BasicToilet(id: annotation.id!, name: annotation.title!, location:MapCoordinate(latitude: Float(annotation.coordinate.latitude), longitude: Float(annotation.coordinate.longitude)), rating: 0, status: annotation.subtitle!)
                }
                
                
            }
            if let coordinates = ( sender as? CLLocationCoordinate2D){
                if let newToiletViewController = navigationController.childViewControllers[0] as? NewToiletTableViewController {
                    newToiletViewController.container = container
                    newToiletViewController.coordinates = coordinates
                    newToiletViewController.databaseChanged = {
                       self.refreshToilets(force: true)
                    }
                    
                }
                
            }
        }
    }
    
    private func valid(timeStamp : Date?) -> Bool{
        return timeStamp == nil ? false : timeStamp!.addingTimeInterval(1000) > Date.init() ? true : false
    }
    
    
    private func databaseUpdated(){
        DispatchQueue.main.async {
            self.updateContexOfControllers()
        }
       
    }
    
    func getRating(forToilet toilet:Toilet){
        if let url = URL(string : URLStorage.getRating(forToiletId: Int(toilet.id)))  {
            URLSession.shared.dataTask(with: url) { data, response, err in
                if let data = data{
                    do{
                        let newRatings = try JSONDecoder().decode([BasicRating].self, from: data)
                        self.container?.performBackgroundTask{ contex in
                            let ownerToilet  = try! Toilet.findToilet(withId: toilet.id, in: contex)
                            //TODO: do something with data
                            if let ratingCount = try? Rating.numberOfToilets(InDatabase: contex){
                                if ratingCount < newRatings.count{
                                    for rating in newRatings{
                                        let rating = try! Rating.findOrCreateRating(matching: rating, in: contex,forToilet: toilet)
                                        ownerToilet!.addToRatings(rating)
                                        rating.toilet = ownerToilet
                                    }
                                    
                                    do{
                                        try contex.save()
                                    }
                                    catch{
                                        print("error during saving database")
                                    }
                                }
                            }
                            
                            
                        }
                        
                    }catch let jsonErr{
                        print("Error serializing json:" ,jsonErr)
                    }
                }
                else{
                    print("error")
                }
                }.resume()
        }
    
        
    
    }
    
    func refreshToilets(force:Bool){
        let appdelegate = UIApplication.shared.delegate as! AppDelegate
        if !valid(timeStamp: appdelegate.getTimeStamp()) || force{
            if let url = URL(string : URLStorage.getToilets)  {
                URLSession.shared.dataTask(with: url) { data, response, err in
                    if let data = data{
                        do{
                            let newToilets = try JSONDecoder().decode([BasicToilet].self, from: data)
                            appdelegate.persistentContainer.performBackgroundTask{ contex in
                                for toiletInfo in newToilets{
                                    _ = try? Toilet.findOrCreateToilet(matching: toiletInfo, in: contex)
                                }
                                do{
                                    try contex.save()
                                    appdelegate.refreshTimeStamp()
                                    self.databaseUpdated()
                                }catch{
                                    print("error")
                                }
                            }
                            
                        }catch let jsonErr{
                            print("Error serializing json:" ,jsonErr)
                        }
                    }
                    else{
                        print("error")
                    }
                    }.resume()
            }
        }
        
        
    }
    
}
