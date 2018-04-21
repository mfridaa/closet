//
//  ToiletInformationsTableViewController.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 13..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit
import CoreData
import MapKit

class ToiletInformationsTableViewController: UITableViewController {
    
    var container:NSPersistentContainer? = (UIApplication.shared.delegate as! AppDelegate).persistentContainer
    
    var coordinates:CLLocationCoordinate2D?
    
    
    class ConstansIdentifiers{
        static let NewPlace:String = "Editable Cell"
        static let ExistingPlace:String = "Simple Cell"
        static let Date:String = "Date Picker"
        static let ToiletIsReady:String = "Add Toilet"
    }
    
    var toilet:Toilet?
    
    var newName:String?
    
    
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        if toilet?.name != nil {
            return "Toilet informations"
        }
        else{
            if section == 0{
                return "Toilet informations"
            }
            else{
                return "Opening hours"
            }
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == ConstansIdentifiers.ToiletIsReady {
            if let context = container?.viewContext{
                toilet = Toilet(context: context)
                toilet?.name = newName ?? "No name"
                toilet?.id = 100
                toilet?.latitude = Float((coordinates?.latitude)!)
                toilet?.longitude = Float((coordinates?.longitude)!)
                do{
                    try context.save()
//                    let number = try Toilet.numberOfToilets(InDatabase: context)
//                    let toilets = try Toilet.allToilets(inDatabase: context)
//                    DispatchQueue.main.async {
//                        print("elements in database: \(number)")
//                        print("toilets:\(toilets)")
//                    }
                }catch{
                    print("error")
                }
            }
//            print(toilet?.longitude)
//            print(toilet?.latitude)
//            _ = try! Toilet.findOrCreateToilet(matching: toilet!, in: context!)
            
            
            
            
            
//            let toilet: Toilet = Toilet()
            
        }
    }
    
    
//    private func addNewToilet() -> Int{
//
//            if let url = URL(string : URLStorage.addToilet)  {
//                URLSession.shared.dataTask(with: url) { data, response, err in
//                    if let data = data{
//                        do{
//                            
//                            let newToilets = try JSONDecoder().decode([BasicToilet].self, from: data)
//                            appdelegate.persistentContainer.performBackgroundTask{ contex in
//                                for toiletInfo in newToilets{
//                                    _ = try? Toilet.findOrCreateToilet(matching: toiletInfo, in: contex)
//                                }
//                                do{
//                                    try contex.save()
//                                    
//                                    appdelegate.refreshTimeStamp()
//                                }catch{
//                                    print("error")
//                                }
//                                
//                                
//                            }
//                            
//                        }catch let jsonErr{
//                            print("Error serializing json:" ,jsonErr)
//                        }
//                    }
//                    else{
//                        print("error")
//                    }
//                    }.resume()
//            }
//            appdelegate.persistentContainer.performBackgroundTask{ contex in
//                do{
//                    let number = try Toilet.numberOfToilets(InDatabase: contex)
//                    DispatchQueue.main.async {
//                        print("elements in database: \(number)")
//                    }
//                    
//                }
//                catch{
//                    DispatchQueue.main.async {
//                        print(0)
//                    }
//                }
//            }
//        }
//    }
    
    
//
//    @IBOutlet weak var nameLabel: UILabel!
//        {
//        didSet{
//            nameLabel.text = toiletName
//        }
//    }
//
//    @IBOutlet weak var ratingLabel: UILabel!
//        {
//        didSet{
//            ratingLabel.text = String(format:"%.02f", (ratingValue)!)
//        }
//    }
//
//    @IBOutlet weak var statusLabel: UILabel!
//        {
//        didSet{
//            statusLabel.text = status
//        }
//    }
    
    @IBOutlet weak var newRatingValue: UISlider!
    
//    @IBAction func rateHappened(_ sender: UIButton) {
//        
//    }
//    
//    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
//        if let ratingController = segue.destination as? RatingTableViewController{
//            let tableView = UITableView.init()
//            tableView.delegate = ratingController
//            ratingController.view = tableView
//            let container = ( UIApplication.shared.delegate as! AppDelegate ).persistentContainer
//            container.performBackgroundTask{ context in
////                let toilet = try! Toilet.findToilet(withId: (self.toiletId)!, in: context)
////                DispatchQueue.main.async {
////                    print(toilet?.ratings)
////                }
//                let toilet = try! context.fetch(Rating.fetchRequest())
//                if let ratings = toilet as? [Rating]{
//                    for rating in ratings{
//                        print(rating.toilet?.id)
//                    }
//                }
//            }
//
//
//        }
//    }
    @IBOutlet weak var cancelButton: UIBarButtonItem!
    
    @IBOutlet weak var doneButton: UIBarButtonItem!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if toilet?.name != nil{
            self.navigationItem.rightBarButtonItems = nil
           
            print("asd")
//            view.willRemoveSubview(doneButton)
        }
    }

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        if toilet?.name != nil{
            return 1
        }
        else{
            return 2
        }
        
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        if section == 0{
            if toilet?.name != nil{
                return 3
            }
            else{
                return 1
            }
            
        }
        else{
            return 7
        }
        
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.section == 0{
            if let toilet = toilet,toilet.name != nil {
                let cell = tableView.dequeueReusableCell(withIdentifier:  ConstansIdentifiers.ExistingPlace, for: indexPath)
                if indexPath.row == 0{
                    cell.textLabel?.text = "Name"
                    cell.detailTextLabel?.text = toilet.name
                }
                else if indexPath.row == 1{
                    cell.textLabel?.text = "Rating"
                    cell.detailTextLabel?.text = String(toilet.rating )
                }
                else if indexPath.row == 2{
                    cell.textLabel?.text = "Status"
                    cell.detailTextLabel?.text = toilet.status
                }
                return cell
            }
            else{
                if let cell = ( tableView.dequeueReusableCell(withIdentifier: ConstansIdentifiers.NewPlace, for: indexPath) as? NewValueTableViewCell ){
                    if indexPath.row == 0{
                        cell.name = "Name"
                        cell.changeHappened = { value in
                            print("im woriking")
                            print(value)
                        }
                        //                    cell.detailTextLabel?.text = toilet.name
                    }
                    else if indexPath.row == 1{
                        cell.name = "Rating"
                        //                    cell.detailTextLabel?.text = String(toilet.rating )
                    }
                    else if indexPath.row == 2{
                        cell.name = "Status"
                        //                    cell.detailTextLabel?.text = toilet.status
                    }
                    return cell
                }
                let cell: NewValueTableViewCell = NewValueTableViewCell()
                
                return cell
                
            }
        }else{
            let cell = tableView.dequeueReusableCell(withIdentifier:  ConstansIdentifiers.Date, for: indexPath)
            
            if let dateCell = ( cell as? DatePickerTableViewCell ) {
                dateCell.day = Day.getDayFor(day: indexPath.row)
            }
            
            return cell
        }
        
        
        
        
       
    }
    
  
    

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
