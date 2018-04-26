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
    
    
    var coordinates:CLLocationCoordinate2D?
    
    var toilet:BasicToilet?

    
    @IBOutlet weak var newRatingValue: UISlider!

    @IBOutlet weak var cancelButton: UIBarButtonItem!
    

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 2
        
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {

                let cell = tableView.dequeueReusableCell(withIdentifier:  "Simple Cell", for: indexPath)
                if indexPath.row == 0{
                    cell.textLabel?.text = "Name"
                    cell.detailTextLabel?.text = toilet?.name
                }

                else if indexPath.row == 1{
                    cell.textLabel?.text = "Status"
                    cell.detailTextLabel?.text = toilet?.status
                }
                return cell
            }
    
    
        
        
        
       
    }
    
  

 


