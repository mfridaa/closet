//
//  NavigationTabBarViewController.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 12..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit
import MapKit

class NavigationTabBarViewController: UITabBarController {

    let container = (UIApplication.shared.delegate as! AppDelegate).persistentContainer
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    override func viewWillAppear(_ animated: Bool) {
        if let controllers = viewControllers{
            for controller in controllers{
                if let toiletController = ( controller as? ToiletDataTableViewController )  {
//                    let appdelegate =  (UIApplication.shared.delegate as! AppDelegate)
                    refreshToilets()
                    let container = (UIApplication.shared.delegate as! AppDelegate).persistentContainer
                    toiletController.container = container
                }
            }
        }
        
    }
    override func viewDidAppear(_ animated: Bool) {
        
    }

    @IBOutlet weak var toiletButton: UITabBar!
    
    override func tabBar(_ tabBar: UITabBar, didSelect item: UITabBarItem){
        //TODO: --set the views data from database
        
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let navigationController = segue.destination as? UINavigationController{
            if let toiletInformationsViewController = navigationController.childViewControllers[0] as? ToiletInformationsTableViewController {
                if let annotationSender = sender as? MKAnnotation {
                    container.performBackgroundTask{ context in
                        if let toilet = try! Toilet.findToilet(longitude: Float(annotationSender.coordinate.longitude), latitude:  Float(annotationSender.coordinate.latitude), in: context){
                            toiletInformationsViewController.status = toilet.status
                            toiletInformationsViewController.toiletName = toilet.name
                            toiletInformationsViewController.ratingValue = toilet.rating
                            self.getRating(forToilet: toilet)
                        }
                    }
                }
            }
        }
       
        
    }
    //selese
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    private func valid(timeStamp : Date?) -> Bool{
        return timeStamp == nil ? false : timeStamp!.addingTimeInterval(90) > Date.init() ? true : false
    }
    
    
    func getRating(forToilet toilet:Toilet){
        if let url = URL(string : URLStorage.getRating(forToiletId: Int(toilet.id)))  {
            URLSession.shared.dataTask(with: url) { data, response, err in
                if let data = data{
                    do{
                        let newRatings = try JSONDecoder().decode([BasicRating].self, from: data)
                        self.container.performBackgroundTask{ contex in
                            //TODO: do something with data
//                            if let ratingCount = try? Rating.numberOfToilets(InDatabase: contex){
//                                if ratingCount < newRatings.count{
                                    for rating in newRatings{
                                        _ = try! Rating.findOrCreateRating(matching: rating, in: contex,forToilet: toilet)
                                        print("asd")
                                    }
                                    
                                    do{
                                        try contex.save()
                                    }
                                    catch{
                                        print("error during saving database")
                                    }
//                                }
//                            }
                            
                            
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
    
    func refreshToilets(){
        let appdelegate = UIApplication.shared.delegate as! AppDelegate
        if !valid(timeStamp: appdelegate.getTimeStamp()){
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
            appdelegate.persistentContainer.performBackgroundTask{ contex in
                do{
                    let number = try Toilet.numberOfToilets(InDatabase: contex)
                    DispatchQueue.main.async {
                        print("elements in database: \(number)")
                    }
                    
                }
                catch{
                    DispatchQueue.main.async {
                        print(0)
                    }
                }
            }
        }
        
        
    }
    
//        if let url = URL(string : URLStorage.getToilets){
////            if let target = ( view as? ToiletDataTableViewController ){
////                target.processStarted()
////            }
//            URLSession.shared.dataTask(with: url) { data, response, err in
//                if let data = data{
//                    do{
//
//                        let newToilets = try JSONDecoder().decode([BasicToilet].self, from: data)
//                        print("ok")
//                        DispatchQueue.main.async {
//                            if let target = ( view as? ToiletDataTableViewController ){
//                                target.processTerminated(with:newToilets)
//                                print("asd")
//                            }
////                            self.processTerminated()
////                            self.toilets = newToilets
////                            print("asd")
////                            print(newToilets)
//                        }
//                    }catch let jsonErr{
//                        print("Error serializing json:" ,jsonErr)
//                    }
//                }
//                else{
//                    print("error")
//                }
//                }.resume()
//        }
//
//    }

//}
}
